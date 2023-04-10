export default class CommentDto {
    constructor(args) {
        this.id = args.id || null; 
        this.text = args.text;
    }
}