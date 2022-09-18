# CodeStyle guide
> We will use Google checkstyle formatting.
>
> To configure it, you need to:
> - Install checkstyle [plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea).
> - Download the configuration xml-file `Checkstyle.xml`.
> - Go to `settings > editor > code style > java` in IntelliJ Idea.
> - `import` scheme from `Checkstyle.xml` file
> - Go to `settings > tools > checkstyle` and turn on Google checks.
> - From the main menu, select `Help | Edit Custom VM Options`.
> - Add this: 
>>```
>> --add-opens=java.base/java.lang=ALL-UNNAMED
>> --add-opens=java.base/java.util=ALL-UNNAMED
>> --add-exports=jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED
>> --add-exports=jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED
>> --add-exports=jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED
>> --add-exports=jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED
>> --add-exports=jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED 
>>```
> - Done
>
