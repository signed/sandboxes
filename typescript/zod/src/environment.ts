import {z} from 'zod'

const EnvironmentVariablesSchema = z.object({BackendUrl: z.string()});
EnvironmentVariablesSchema.parse(process.env);

declare global {
  namespace NodeJS {
    interface ProcessEnv extends z.infer<typeof EnvironmentVariablesSchema> {
    }
  }
}

process.env.BackendUrl
