module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

let word = /[^# \n\t]+/

let separator ( character : string ) =
    del character character

let category = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

let shortcut_key = [ key /([\{\}_-]|[a-zA-Z0-9])+/ ]

let sep_spc = del /[ \t]+/ " "

let key = /([^\\+,]+)/

let key_combination (counter_name:string) =
 [label "key" . [seq counter_name . store key] .  [separator "+" . seq counter_name . store key]*]


let active_shortcut = key_combination "active"
let default_shortcut = key_combination "default"

let binding_key = [label "action" . store /[^=]+/]
let binding_value = [ label "active" . active_shortcut] . separator "," . [label "default" . default_shortcut] . separator "," . [ label "humanreadable" . store /.*/ ]

let binding = binding_key . separator "=" . binding_value

let lns = shortcut_key