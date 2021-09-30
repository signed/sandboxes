type SupportedLanguage = 'EO' | 'EN' | 'ZH' | 'ES'

export interface Language {
    type: 'general.language';
    value: SupportedLanguage;
}
