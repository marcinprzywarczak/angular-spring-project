import { RegisterUser } from './registerUser';

export interface AddNewUser extends RegisterUser {
  role: String[];
}
