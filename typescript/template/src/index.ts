export function hello(world: string = '🗺️'): string {
  return `Hello ${world}!`;
}

enum Taste {
  Sweet = 'sweet',
  Sour = 'sour',
}

interface Fruit {
  taste: Taste;
}

class Apple implements Fruit {
  public taste: Taste = Taste.Sour;
}

class DuckTyping {
  public taste: Taste = Taste.Sweet;
}

export function tasteOf(fruit: Fruit): Taste {
  return fruit.taste;
}

export function someFunction() {
  console.log(tasteOf(new Apple()));
  console.log(tasteOf(new DuckTyping()));
  console.log(tasteOf({ taste: Taste.Sweet }));
}
