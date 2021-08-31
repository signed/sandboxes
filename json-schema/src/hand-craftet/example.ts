interface One {
  type: 'one'
  value: 1
}

interface Two {
  type: 'two'
  value: 2
}

interface Three {
  type: 'three'
  value: 3
}

type Numbers = One | Two | Three

type NumberDocument = {
  'one': One
  'two': Two
  'three': Three
}
type WithDefaults = 'one' | 'three'

// hand coded
type Defaults = {
  [Property in keyof NumberDocument as Extract<Property, WithDefaults>]: NumberDocument[Property]['value']
}

