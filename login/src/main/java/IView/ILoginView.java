package IView;

public interface ILoginView {
    //登录失败
    void Failed();

    //登录成功
    void Succeed();

    //判断账号密码是否为空
    boolean isEmpty(boolean id, boolean password);
}
