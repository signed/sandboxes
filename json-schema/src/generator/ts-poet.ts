import fs from 'fs'
import { code, imp, Code } from 'ts-poet'

const path = process.cwd() + '/schemas/go-wild-schema.json'
console.log(process.cwd())
console.log(__dirname)
console.log(path)
const rawdata = fs.readFileSync(path)
let message = JSON.parse(rawdata.toString('utf-8'))

console.log(message['$comment'])


const Observable = imp('@rxjs/Observable')
const greet: Code = code`
  greet(): ${Observable}<string> {
    return ${Observable}.from(\`Hello $name\`)};
  }
`

const greeter = code`
export class Greeter {

  private name: string;

  constructor(private name: string) {
  }

  ${greet}
}
`

const output = greeter.toStringWithImports()

output.then(code => console.log(code))
