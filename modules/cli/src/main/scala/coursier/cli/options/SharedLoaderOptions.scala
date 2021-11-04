package coursier.cli.options

import caseapp.{ExtraName => Short, HelpMessage => Help, ValueDescription => Value, _}
import coursier.install.RawAppDescriptor


// format: off
final case class SharedLoaderOptions(

  // deprecated, use shared instead
  @Group("Launch")
  @Hidden
  @Value("target:dependency")
  @Short("I")
  @Help("(deprecated) dependencies to be put in shared class loaders")
    isolated: List[String] = Nil,

  @Group("Launch")
  @Hidden
  @Value("dependency[@target]")
  @Help("Dependencies to be put in shared class loaders")
    shared: List[String] = Nil,

  @Group("Launch")
  @Hidden
  @Help("Comma-separated isolation targets")
  @Short("i")
  @Short("isolateTarget") // former deprecated name
    sharedTarget: List[String] = Nil

) {
  def addApp(app: RawAppDescriptor): SharedLoaderOptions =
    copy(
      shared = {
        val previous = shared
        previous ++ app.shared.filterNot(previous.toSet)
      }
    )
}
// format: on

object SharedLoaderOptions {
  implicit val parser = Parser[SharedLoaderOptions]
  implicit val help   = caseapp.core.help.Help[SharedLoaderOptions]
}
