import {compare, Framework, github} from "./compare.js";

const frameworks: Framework [] = [
  github('axios', 'https://github.com/axios/axios'),
]

await compare(frameworks)
