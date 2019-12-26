<h1>Code Inspection Plugin for IntellIJ 2018</h1>


<h3>Run demo by executing gradle task runIde</h3> 
<code>gradle runIde</code>

About <br>
These plugin adds code inspection for Java under: <br>Java Swing -> Custom -> Do not use SwingUtilities.invokeLater
<br>
and for Kotlin under:<br> Kotlin Custom -> Don't use SwingUtilities.invokeLater <br>

Marks usage of the following as an error, (Java Swing) <br>
<code>SwingUtlities.invokeLater(Runnable) </code>
<br>
<h4>Tests available for the Java version of the plugin</h4>