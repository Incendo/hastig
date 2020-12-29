rootProject.name = "hastig"

include("worldedit-libs")

listOf("bukkit", "core").forEach {
    include("worldedit-libs:$it")
    include("worldedit-$it")
}
include("worldedit-libs:core:ap")
