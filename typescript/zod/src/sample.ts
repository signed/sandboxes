import { z } from "zod";

const envVariables = z.object({
  DATABASE_URL: z.string(),
  CUSTOM_STUFF: z.string(),
});

envVariables.parse(process.env);

declare global {
  namespace NodeJS {
    interface ProcessEnv extends z.infer<typeof envVariables> {}
  }
}

process.env.CUSTOM_STUFF
