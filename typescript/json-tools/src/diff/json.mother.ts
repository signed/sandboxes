export type DiffInput = {original:object, candidate: object;}


export const starWars = ():DiffInput=> {
  const original = {
    planet: 'Tatooine',
    faction: 'Jedi',
    characters: [
      { id: 'LUK', name: 'Luke Skywalker', force: true },
      { id: 'LEI', name: 'Leia Organa', force: true }
    ],
    weapons: ['Lightsaber', 'Blaster']
  };

  const candidate = {
    planet: 'Alderaan',
    faction: 'Rebel Alliance',
    characters: [
      { id: 'LUK', name: 'Luke Skywalker', force: true, rank: 'Commander' },
      { id: 'HAN', name: 'Han Solo', force: false }
    ],
    weapons: ['Lightsaber', 'Bowcaster', 'Blaster']
  };
  return  {original, candidate}
}


export const deepNestedChange = (): DiffInput => {
  return {
    original: {
      array: [
        {
          fruit: 'banana',
          facts: [{
            type: 'country',
            value: 'Vietnam'
          }]
        },
        {
          fruit: 'apple'
        }
      ]
    },
    candidate: {
      array: [
        {
          fruit: 'banana',
          facts: [{
            type: 'country',
            value: 'Panama'
          }]
        },
        {
          fruit: 'apple'
        }
      ]
    }
  }
}
