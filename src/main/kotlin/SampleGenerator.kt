import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
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

  val extensionToTitleCaseFunSpec = FunSpec
      .builder("toTitleCase")
      .receiver(String::class)
      .returns(String::class)
      .addStatement("return this.toLowerCase().capitalize()")
      .build()

  val sampleClassTypeSpec = TypeSpec.classBuilder(className)
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
          .addStatement("println(%S.%N())", "\$name", extensionToTitleCaseFunSpec)
          .build()
      )
      .build()

  val mainFunSpec = FunSpec
      .builder("main")
      .addParameter("args", String::class, KModifier.VARARG)
      .addStatement("Sample(\"Hello, \\nWorld\").prettyPrint()")
      .build()

  val personDataClassTypeSpec = TypeSpec
      .classBuilder("Person")
      .addModifiers(KModifier.DATA)
      .addProperty(
          PropertySpec
              .builder("age", Int::class)
              .initializer("age")
              .mutable(false)
              .build()
      )
      .primaryConstructor(
          FunSpec
              .constructorBuilder()
              .addParameter(
                  ParameterSpec
                      .builder("age", Int::class)
                      .build()
              )
              .build()
      )
      .build()

  FileSpec
      .builder(outPackageName, outFileName)
      .addComment("This should be replaced by custom copyright files")
      .addType(sampleClassTypeSpec)
      .addType(personDataClassTypeSpec)
      .addFunction(extensionToTitleCaseFunSpec)
      .addFunction(mainFunSpec)
      .build()
      .writeTo(OUTPUT_DIR)
}
