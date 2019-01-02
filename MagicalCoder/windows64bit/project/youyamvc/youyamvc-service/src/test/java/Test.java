import com.magicalcoder.youyamvc.app.model.Teacher;
import com.magicalcoder.youyamvc.app.utils.ProjectUtil;

/**
 * Created by hzhedongyu on 2016/3/16.
 */
public class Test {

    public static void main(String[] args) throws IllegalAccessException {
        Teacher teacher = new Teacher();
        teacher.setTeacherName("hdy");

        String a = ProjectUtil.reflectShowValue("teacherName,age",teacher);
        System.out.println(a);
    }
}
