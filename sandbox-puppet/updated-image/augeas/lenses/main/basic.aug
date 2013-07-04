module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

(* keywords and values are separated by a colon *)
let colon = del /:[ \t]*/ ": "

(* note that no space is allowed between "Source" and ':' *)
let source = [ key "Source" . colon . store /[^ \t]+/ . eol ]

let row = [ del /\[/ "[" . key /[a-zA-Z ]+/ . del /\]/ "]" . eol]

(* this lens will get more complex in time *)
let lns = source
