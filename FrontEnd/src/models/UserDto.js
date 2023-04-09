export default class BuyerDto {
    constructor(args) {
        this.id = args.id || null; 
        this.firstName = args.firstName;
        this.lastName = args.lastName;
        this.email = args.email;
    }
}