import com.intellij.codeInspection.InspectionToolProvider

class CustomInspectionProvider : InspectionToolProvider {
    /**
     * Query method for inspection tools provided by a plugin.
     * @return classes that extend [InspectionProfileEntry]
     */
    override fun getInspectionClasses(): Array<Class<*>> {
        return arrayOf( CamelcaseInspection::class.java, InvokeLaterKotlinInspection::class.java)
    }


}