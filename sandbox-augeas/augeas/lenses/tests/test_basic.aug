module Test_basic =

let category = "[naMe me]\n"
test Basic.category get category = {"naMe me"}

test Basic.shortcut_key get "-_{a_b-}0" = { "-_{a_b-}0" }

test Basic.keys get "Ctrl Alt R" = {"key" = "Ctrl"} {"key" = "Alt"}{"key"= "R"}