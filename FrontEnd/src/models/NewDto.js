export default class NewDto {
    constructor(data) {
        this.id = data?.id || null;
        this.image = data?.image;
        this.heading = data?.heading;
        this.content = data?.content;
    }
    
}