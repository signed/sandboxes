module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]
let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

(* this lens will get more complex in time *)
let lns = shortcut_key
