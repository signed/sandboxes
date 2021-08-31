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
type Types = Numbers['type']

type NumberDocument = {
  'one': One
  'two': Two
  'three': Three
}
type WithDefaults = 'one' | 'three'

