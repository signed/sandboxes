import {test, expectTypeOf} from "vitest";
import {findOne, Fruit, FruitSchema, User, UserSchema} from "./pass-schema-to-function.js";

test('passing schemas to a function', async () => {
  const user = await findOne(UserSchema);
  expectTypeOf(user).toMatchTypeOf<User>()

  const fruit = await findOne(FruitSchema);
  expectTypeOf(fruit).toMatchTypeOf<Fruit>()
});
