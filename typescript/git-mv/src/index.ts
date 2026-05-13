export function hello(world: string = '🗺️'): string {
  return `Hello ${world}!`
}

type Taste = 'Sweet' | 'Sour'

interface Fruit {
  taste: Taste
}

class Apple implements Fruit {
  public taste: Taste = 'Sour'
}

class DuckTyping {
  public taste: Taste = 'Sweet'
}

export function tasteOf(fruit: Fruit): Taste {
  return fruit.taste
}

export function basicInterfaces() {
  console.log(tasteOf(new Apple()))
  console.log(tasteOf(new DuckTyping()))
  console.log(tasteOf({ taste: 'Sweet' }))
}
