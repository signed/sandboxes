module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let word = /[^# \n\t]+/

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

let sep_spc = del /[ \t]+/ " "

let key = /([^\\+,]+)/

let keys = [label "key" . [seq "key-stroke" . store key] .  [del "+" "+" . seq "key-stroke" . store key]*]
let active_keys = [label "key" . [seq "active-key-stroke" . store key] .  [del "+" "+" . seq "active-key-stroke" . store key]*]

let binding_value_separator = del /,/ ","

let binding_value = [ label "active" . active_keys] . binding_value_separator . [label "default" . keys] . binding_value_separator . [ label "humanreadable" . store /.*/ ]

(* [ key k . colon . [ seq k . store email] . [ seq k . sep_comma_with_nl . store email ]* . eol ] *)

let lns = shortcut_key