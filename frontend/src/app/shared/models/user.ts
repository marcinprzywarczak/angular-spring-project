export interface User {
  id: number;
  name: string;
  email: string;
  roles: { id: number; name: string }[];
}
