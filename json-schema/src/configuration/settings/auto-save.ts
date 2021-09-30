export interface AutoSave {
    type: 'editor.auto-save';
    value: {
        value: boolean;
        interval: number;
    };
}
