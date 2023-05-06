export default class UserSignupDto {
    constructor(args) {
        this.login = args.login;
        this.email = args.email;
        this.password = args.password;
        this.passwordConfirm = args.passwordConfirm;
    }
}