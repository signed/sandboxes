import type { Framework } from './compare.js'
import { compare, github } from './compare.js'

// https://github.com/sindresorhus/got#comparison
// https://www.memberstack.com/blog/node-http-request
const frameworks: Framework[] = [
  github('axios', 'https://github.com/axios/axios'),
  github('zodios', 'https://github.com/ecyrbe/zodios'),
  github('superagent', 'https://github.com/ladjs/superagent'),
  github('got', 'https://github.com/sindresorhus/got'),
  github('ky', 'https://github.com/sindresorhus/ky'),
  github('node-fetch', 'https://github.com/node-fetch/node-fetch'),
]

await compare(frameworks)
