import com.intellij.codeInsight.daemon.GroupNames
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.psi.namedDeclarationVisitor

class CamelcaseInspection : AbstractKotlinInspection() {

    override fun getDisplayName(): String {
        return "Use CamelCase naming"
    }

    override fun getGroupDisplayName(): String {
        return GroupNames.STYLE_GROUP_NAME
    }

    override fun getShortName(): String {
        return "CamelCase"
    }

    override fun buildVisitor(holder: ProblemsHolder,
                              isOnTheFly: Boolean): KtVisitorVoid {
        return namedDeclarationVisitor { declaredName ->
            if (declaredName.name?.isDefinedCamelCase() == false) {
                println(
                    "Non CamelCase Name Detected for${declaredName.name}"
                )
                holder.registerProblem(
                    declaredName.nameIdentifier as PsiElement,
                    "Please use CamelCase for #ref #loc")
            }
        }
    }

    private fun String.isDefinedCamelCase(): Boolean {
        val toCharArray = toCharArray()
        return toCharArray
            .mapIndexed { index, current ->
                current to toCharArray.getOrNull(index + 1) }
            .none { it.first.isUpperCase() &&
                    it.second?.isUpperCase() ?: false }
    }

    override fun isEnabledByDefault(): Boolean {
        return true
    }
}