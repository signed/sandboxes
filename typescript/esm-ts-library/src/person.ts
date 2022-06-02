export default class Person {
  constructor(public id: number, public name: string) {
    // empty
  }
}

export const sortByName = (persons: Person[]): Person[] => {
  return persons.sort((a, b) => a.name.localeCompare(b.name));
};
