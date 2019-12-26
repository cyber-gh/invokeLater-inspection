import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.codeInspection.ex.BaseLocalInspectionTool;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiImportStaticStatementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class InvokeLaterInspection extends BaseLocalInspectionTool {

    private final String methodCall = "SwingUtilities.invokeLater";
    private final String methodImportQualifiedName = "javax.swing.SwingUtilities";
    private final String methodName = "invokeLater";

    private final String problemDescription = "Usage of SwingUtilities.invokeLater is prohibited";

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {

            private boolean isStaticallyImported = false;


            @Override
            public void visitImportList(PsiImportList list) {
                isStaticallyImported = false;
                for (PsiImportStaticStatement importStaticStatement : list.getImportStaticStatements()) {
                    PsiImportStaticStatementImpl staticImport = (PsiImportStaticStatementImpl) importStaticStatement;
                    if (staticImport == null) continue;
                    if (
                            Objects.requireNonNull(staticImport.getClassReference()).getQualifiedName().equals(methodImportQualifiedName) &&
                                    Objects.equals(staticImport.getReferenceName(), methodName)
                    ) {
                        holder.registerProblem(importStaticStatement, problemDescription);
                        isStaticallyImported = true;
                    }

                }
                super.visitImportList(list);
            }

            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                if (expression.getMethodExpression().getText().equals(methodCall)) {
                    holder.registerProblem(expression, problemDescription);
                }
                if (expression.getMethodExpression().getText().equals(methodName)) {
                    if (isStaticallyImported) {
                        holder.registerProblem(expression, problemDescription);
                    }
                }

                super.visitMethodCallExpression(expression);
            }
        };
    }

    @Override
    public void inspectionFinished(@NotNull LocalInspectionToolSession session, @NotNull ProblemsHolder problemsHolder) {
        super.inspectionFinished(session, problemsHolder);

    }
}
