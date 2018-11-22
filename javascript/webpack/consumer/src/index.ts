import hello from '@signed/library'

export function conversation(): void {
  let greeting = hello('you');
  console.log(greeting);
}