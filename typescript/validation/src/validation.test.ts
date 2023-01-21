import { describe, it, expect } from 'vitest';
import { z, ZodError } from 'zod';

describe('string schema', () => {
    
    const stringSchema = z.string();
    
    describe('parse', () => {
        it('string successful', () => {
            const input: unknown = 'tuna';
            const parsed = stringSchema.parse(input);
            //    ^? const parsed: string
            expect(parsed).toEqual('tuna')
        });
        
        it('number with error', () => {
            const input: unknown = 12;
            expect(() => stringSchema.parse(input)).toThrow(ZodError);
        });
    });
    
    describe('safeParse', () => {
        it('string successful', () => {
            const input: unknown = 'tuna';
            const parseResult = stringSchema.safeParse(input);
            expect(parseResult.success).toBe(true);
            if (parseResult.success) {
                expect(parseResult.data).toEqual('tuna')
            }
        });
        
        it('number with error', () => {
            const input: unknown = 12;
            const parseResult = stringSchema.safeParse(input);
            expect(parseResult.success).toBe(false)
            if(!parseResult.success) {
                expect(parseResult.error).toBeDefined()
            }
        });
    });
});