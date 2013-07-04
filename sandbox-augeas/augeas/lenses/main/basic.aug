module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let word = /[^# \n\t]+/

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

let sep_spc = del /[ \t]+/ " "
let keys = [ label "key" . store word ] .[ label "key" . sep_spc . store word ]*

let try = [ label "key". store /[a-z]+/ ].[ label "key". del "+" "+" . store /[a-z]+/ ]+

let lns = shortcut_key
