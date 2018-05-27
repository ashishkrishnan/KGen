import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.io.File.separator

private val OUTPUT_DIR = File("${File("").absolutePath}${separator}src${separator}main${separator}kotlin${separator}")

/*
 * A simple sample for the understanding Code generation using Square's Kotlin Poet.
 */
fun main(args: Array<String>) {
    val outPackageName = "_generated.me.ashishkrishnan.sample"
    val outFileName    = "Sample"
    val className      = ClassName(packageName = outPackageName, simpleName = outFileName)

    FileSpec
        .builder(outPackageName, outFileName)
        .addComment("This should be replaced by custom copyright files")
        .addType(TypeSpec.classBuilder(className)
            .primaryConstructor(FunSpec
                .constructorBuilder()
                .addParameter("name", String::class)
                .build()
            )
            .addProperty(PropertySpec
                .builder("name", String::class)
                .initializer("name")
                .build()
            )
            .addFunction(FunSpec
                .builder("prettyPrint")
                .addStatement("println(%S)", "\$name")
                .build()
            )
            .build()
        )
        .addFunction(FunSpec
            .builder("main")
            .addParameter("args", String::class, KModifier.VARARG)
            .addStatement("Sample(\"Hello, \\nWorld\").prettyPrint()")
            .build()
        )
        .build()
        .writeTo(OUTPUT_DIR)
}
