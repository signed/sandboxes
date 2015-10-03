class Dashing.BambooPlanWithBranches extends Dashing.Widget
  ready: ->
    # This is fired when the widget is done being rendered
    @readyvalue = 'set in the ready call'


  onData: (data) ->
    # Handle incoming data
    # You can access the html node of this widget with `@node`
    # Example: $(@node).fadeOut().fadeIn() will make the node flash each time data comes in.

  @accessor 'any_plan_failed', ->
    @get('failed_plans').length >= 0

#  @accessor 'bamboo_not_reachable', ->
#    false