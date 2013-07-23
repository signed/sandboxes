module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let word = /[^# \n\t]+/

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

let sep_spc = del /[ \t]+/ " "

let key = /([^\\+,]+)/

let key_combination (counter_name:string) =
 [label "key" . [seq counter_name . store key] .  [del "+" "+" . seq counter_name . store key]*]


let active_shortcut = key_combination "active"
let default_shortcut = key_combination "default"

let binding_value_separator = del /,/ ","

let binding_value = [ label "active" . active_shortcut] . binding_value_separator . [label "default" . default_shortcut] . binding_value_separator . [ label "humanreadable" . store /.*/ ]

let lns = shortcut_key