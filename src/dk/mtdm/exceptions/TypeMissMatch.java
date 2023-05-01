package dk.mtdm.exceptions;


public class TypeMissMatch extends Throwable {
  
  Enum one;
  Enum two;
  String msg;

  public TypeMissMatch(String msg,Enum existing, Enum addition){
    one = existing;
    two = addition;
    this.msg = msg;
  }
  @Override
  public void printStackTrace() {
    System.out.println("two enums dont fit");
    System.out.println(one.name());
    System.out.print(" is not applicable with ");
    System.out.print(two.name() + "\n");
    System.out.println(msg);
    super.printStackTrace();
  }
}
