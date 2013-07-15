module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let word = /[^# \n\t]+/

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

let sep_spc = del /[ \t]+/ " "

let key = /([^\\+]+)/

let keys = [ label "key". store key ].[ label "key". del "+" "+" . store key ]*

let lns = shortcut_key
