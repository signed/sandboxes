module Kglobalshortcuts =
    autoload xfm

    (* Define empty line *)
    let empty = [ del /[ \t]*\n/ "" ]


    let lns = (application_shortcuts| empty)*

    let filter = incl "/etc/apt/preferences"
               . Util.stdexcl

    let xfm = transform lns filter