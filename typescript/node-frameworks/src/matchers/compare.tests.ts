import {test, expect} from "vitest";
import {github} from "../compare.js";

test('parse owner and repo from repository url', () => {
  const framework = github('blub', 'https://github.com/nestjs/nest');
  expect(framework.github.owner).toEqual('nestjs')
  expect(framework.github.repo).toEqual('nest')
});
