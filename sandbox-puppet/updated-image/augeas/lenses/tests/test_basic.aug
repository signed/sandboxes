module Test_basic =

(* the source line *)
let source = "Source: libtest-distmanifest-perl\n"

(* declare the lens to test and the resulting tree^Wtwig *)
test Basic.source get source = { "Source" = "libtest-distmanifest-perl" }

let row = "[naMe me]\n"
test Basic.row get row = {"naMe me"}