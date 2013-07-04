module Basic =

(* import eol from util.aug lens *)
let eol = Util.eol

(* keywords and values are separated by a colon *)
let colon = del /:[ \t]*/ ": "

(* note that no space is allowed between "Source" and ':' *)
let source = [ key "Source" . colon . store /[^ \t]+/ . eol ]

(* this lens will get more complex in time *)
let lns = source

(* lens must be used with AUG_ROOT set to debian package source directory *)
let xfm = transform lns (incl "/debian/control")

