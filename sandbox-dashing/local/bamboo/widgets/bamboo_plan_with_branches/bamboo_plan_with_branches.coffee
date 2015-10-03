class Dashing.BambooPlanWithBranches extends Dashing.Widget
  ready: ->
    # This is fired when the widget is done being rendered
    @readyvalue = 'set in the ready call'


  onData: (data) ->
    # Handle incoming data
    # You can access the html node of this widget with `@node`
    # Example: $(@node).fadeOut().fadeIn() will make the node flash each time data comes in.

  @accessor 'plan', ->
    "the plan from coffee"
