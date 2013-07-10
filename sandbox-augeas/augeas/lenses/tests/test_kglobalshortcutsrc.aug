module Test_kglobalshortcutsrc =

let conf ="[KDE Keyboard Layout Switcher]
Switch to Next Keyboard Layout=none,Ctrl+Alt+K,Switch to Next Keyboard Layout
_k_friendly_name=KDE Keyboard Layout Switcher

[kactivitymanagerd]
_k_friendly_name=KDE-Aktivitätenverwaltung
switch-to-activity-87f827c5-d591-4a98-bf4a-5bcbf05a20d4=none,none,Switch to activity \"Neue Aktivität\"
"

     test Kglobalshortcuts.lns get conf =
        { "KDE Keyboard Layout Switcher"
        }
        {}
        { "kactivitymanagerd"
        }