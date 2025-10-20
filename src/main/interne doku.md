# Interne Dokumentation

## Custom Slash-Commands und ihre Optionen

Um die angegebenen Inhalte einer option bei einem Slash command zu verwenden, müssen in der Datenbank folgende Tabellen befüllt werden:
- `command` Mit den Slash command daten
- `command_action` Alle aktionen, die bei dem Ausführen eines Slash commands ausgeführt werden sollen
- `command_option` Alle optionen, die ein Slash command haben soll
- `action_type_variable` Dies zeigt an, welche aktion mit welchem variable_key ersetzt bzw. überschrieben werden kann. Dies funktioniert nur, wenn die action (welcher dieser variable_key attributiert ist) dem command hinzugefügt wurde.
- `command_option_action_type_variable` Hiermit wird die Option mit der action type (für die variabel erkennung) und der genauen action (zur ersetzung der richtigen inhalte) verknüpft, wodurch die Ausführung der action den Wert der option annimmt anstelle nach einem gesetzten Wert in der Datenbank zu suchen.
  - Jede action hat IMMER ein gesetzten fallback Wert, welcher beim Erstellen der Action angegeben wird *(Dies kann ggf. noch geändert werden)*. Dieser befindet sich in den datasource tabellen (Tabellen die als action tables festgelegt sind, bzw. `message` oder `role`.), da die gleichen Actions auch einem Button hinzugefügt werden kann, welcher KEIN input hat. Ohne gesetzten Fallback wert, würde entweder ein Error auftreten, oder ein globaler Fallback, welcher vom Programm gesetzt wird.