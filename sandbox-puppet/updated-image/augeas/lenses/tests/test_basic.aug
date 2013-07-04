module Test_basic =

(* the source line *)
let source = "Source: libtest-distmanifest-perl\n"

(* declare the lens to test and the resulting tree^Wtwig *)
test Basic.source get source = { "Source" = "libtest-distmanifest-perl" }

let category = "[naMe me]\n"
test Basic.category get category = {"naMe me"}

let friendlyName = "_k_friendly_name=KDE-Aktivitätenverwaltung"
test Basic.friendlyName get friendlyName = ?