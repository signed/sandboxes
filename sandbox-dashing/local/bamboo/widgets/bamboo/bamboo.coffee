class Dashing.Bamboo extends Dashing.Widget
  ready: ->
    # This is fired when the widget is done being rendered
    @readyvalue = 'set in the ready call'


  onData: (data) ->
    # Handle incoming data
    # You can access the html node of this widget with `@node`
    # Example: $(@node).fadeOut().fadeIn() will make the node flash each time data comes in.

  @accessor 'any_plan_failed', ->
    not @get('plan_does_not_exist') && @get('failed_plans').length >= 0

  @accessor 'no_plan_failed', ->
    not @get('plan_does_not_exist') && @get('failed_plans').length == 0

  @accessor 'connected', ->
    if @get('bamboo_not_reachable')
      return 'icon-unlink'
    else if @get('plan_does_not_exist')
      return 'icon-frown'
    else
      return 'hidden'
