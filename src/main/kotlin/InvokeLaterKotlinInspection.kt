import com.intellij.codeHighlighting.HighlightDisplayLevel
import com.intellij.codeInsight.daemon.GroupNames
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.debugger.sequence.psi.callName
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.*

class InvokeLaterKotlinInspection : AbstractKotlinInspection() {


    override fun getDisplayName(): String {
        return "Don't use SwingUtilities.invokeLater"
    }

    override fun getGroupDisplayName(): String {
        return "Kotlin Custom"
    }


    override fun getShortName(): String {
        return "InvokeLaterKt"
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }

    override fun getDefaultLevel(): HighlightDisplayLevel {
        return HighlightDisplayLevel.ERROR
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): KtVisitorVoid {
        return object : KtVisitorVoid() {
            private val springUtilitiesImportPathFull = "javax.swing.SwingUtilities.invokeLater"
            private val springUtilitiesImportPath = "javax.swing.SwingUtilities"
            private val methodName = "invokeLater"

            private var isSpringUtilitiesImported = false

            override fun visitCallExpression(expression: KtCallExpression) {
                super.visitCallExpression(expression)


                if (expression.callName() == methodName && isSpringUtilitiesImported) {
                    holder.registerProblem(expression as PsiElement,
                        "Use of SwingUtilities.invokeLater is prohibited")
                }
            }

            override fun visitImportList(importList: KtImportList) {
                isSpringUtilitiesImported = false
                importList.imports.forEach { importDirective ->
                    if (importDirective.importedReference?.text == springUtilitiesImportPath ||
                        importDirective.importedReference?.text == springUtilitiesImportPathFull) {
                        isSpringUtilitiesImported = true
                    }
                }

            }
        }

    }
}