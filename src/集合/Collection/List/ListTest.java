package 集合.Collection.List;

import org.junit.Test;

import java.io.Serializable;
import java.util.*;

public class ListTest {


    /*** Java 集合可分为 Collection 和 Map 两种体系
     *
     * Collection接口：单列数据，定义了存取一组对象的方法的集合
     *      List：元素有序、可重复的集合
     *      Set：元素无序、不可重复的集合
     *
     *  Map接口：双列数据，保存具有映射关系“key-value对”的集合
     */

    /**Collection
     * Collection 接口是 List、Set 和 Queue 接口的父接口，该接口里定义的方法
     * 既可用于操作 Set 集合，也可用于操作 List 和 Queue 集合。
     *
     * JDK不提供此接口的任何直接实现，而是提供更具体的子接口(如：Set和List)
     * 实现。
     *
     * 在 Java5 之前，Java 集合会丢失容器中所有对象的数据类型，把所有对象都
     * 当成 Object 类型处理；从 JDK 5.0 增加了泛型以后，Java 集合可以记住容
     * 器中对象的数据类型。
     *
     *
     *
     *
     *  Collection 接口方法
     * 1、添加
     *       add(Object obj)
     *       addAll(Collection coll) 2、获取有效元素的个数
     *       int size()
     * 3、清空集合
     *       void clear()
     * 4、是否是空集合  boolean isEmpty()
     * 5、是否包含某个元素
     *       boolean contains(Object obj)：是通过元素的equals方法来判断是否
     *      是同一个对象
     *       boolean containsAll(Collection c)：也是调用元素的equals方法来比
     *      较的。拿两个集合的元素挨个比较。
     *
     * 6、删除
     *       boolean remove(Object obj) ：通过元素的equals方法判断是否是
     *      要删除的那个元素。只会删除找到的第一个元素
     *       boolean removeAll(Collection coll)：取当前集合的差集
     * 7、取两个集合的交集
     *       boolean retainAll(Collection c)：把交集的结果存在当前集合中，不
     *      影响c 8、集合是否相等
     *       boolean equals(Object obj)
     *      9、转成对象数组
     *       Object[] toArray()
     * 10、获取集合对象的哈希值
     *       hashCode()
     * 11、遍历
     *       iterator()：返回迭代器对象，用于集合遍历
     */


    /**迭代器
     *使用 Iterator 接口遍历集合元素
     *       Iterator对象称为迭代器(设计模式的一种)，主要用于遍历 Collection 集合中的元素。
     *
     *       GOF给迭代器模式的定义为：提供一种方法访问一个容器(container)对象中各个元
     *      素，而又不需暴露该对象的内部细节。迭代器模式，就是为容器而生。类似于“公
     *      交车上的售票员”、“火车上的乘务员”、“空姐”。
     *
     *       Collection接口继承了java.lang.Iterable接口，该接口有一个iterator()方法，那么所
     *      有实现了Collection接口的集合类都有一个iterator()方法，用以返回一个实现了Iterator接口的对象。
     *
     *
     *       Iterator 仅用于遍历集合，Iterator 本身并不提供承装对象的能力。如果需要创建
     *      Iterator 对象，则必须有一个被迭代的集合。
     *
     *       集合对象每次调用iterator()方法都得到一个全新的迭代器对象，默认游标都在集合
     *      的第一个元素之前。
     *
     *
     * Iterator iter = coll.iterator();//回到起点
     * while(iter.hasNext()){
     * Object obj = iter.next();
     * if(obj.equals("Tom")){
     * iter.remove();
     * } }
     *
     *
     *  注意：
     *       Iterator可以删除集合的元素，但是是遍历过程中通过迭代器对象的remove方 法，不是集合对象的remove方法。
     *       如果还未调用next()或在上一次调用 next 方法之后已经调用了 remove 方法，
     *      [再调用remove]都会报IllegalStateException。
     *
     *
     *
     * 使用 foreach 循环遍历集合元素
     *       Java 5.0 提供了 foreach 循环迭代访问 Collection和数组。
     *       遍历操作不需获取Collection或数组的长度，[[无需]使用索引访问元素]。
     *       遍历集合的【底层调用Iterator完成操作】。
     *       foreach还可以用来遍历数组
     */


    /**
     * List接口概述
     *       鉴于Java中数组用来存储数据的局限性，我们通常[使用List替代数组]
     *       [List集合]类中[元素有序]、且[可重复]，集合中的[每个元素都有其对应的顺序索引]。
     *       List容器中的元素都【对应一个整数型的序号记载其在容器中的位置】，可以【根据序号存取容器中的元素】。
     *       JDK API中List接口的实现类常用的有：【ArrayList、LinkedList和Vector。】
     */
    @Test
    public void test01(){
        /**List接口方法
         * List继承的 Collection 的接口方法
         *       1、添加
         *             add(Object obj)
         *             addAll(Collection coll)
         *
         *       2、获取有效元素的个数
         *             int size()
         *       3、清空集合
         *             void clear()
         *       4、是否是空集合
         *             boolean isEmpty()
         *       5、是否包含某个元素
         *             boolean contains(Object obj)：是通过元素的equals方法来判断是否是同一个对象
         *             boolean containsAll(Collection c)：也是调用元素的equals方法来比
         *            较的。拿两个集合的元素挨个比较。
         *
         *       6、删除
         *             boolean remove(Object obj) ：通过元素的equals方法判断是否是
         *            要删除的那个元素。只会删除找到的第一个元素
         *             boolean removeAll(Collection coll)：取当前集合的差集
         *       7、取两个集合的交集
         *             boolean retainAll(Collection c)：把交集的结果存在当前集合中，不
         *            影响c 8、集合是否相等
         *             boolean equals(Object obj)
         *            9、转成对象数组
         *             Object[] toArray()
         *       10、获取集合对象的哈希值
         *             hashCode()
         *       11、遍历
         *             iterator()：返回迭代器对象，用于集合遍历
         */
        /**
         *  List除了【从Collection集合继承的方法外】，List 集合里添加了一些根据索引来操作集合元素的方法。
         *      void add(int index, Object ele):在index位置插入ele元素
         *      boolean addAll(int index, Collection eles):从index位置开始将eles中
         *      的所有元素添加进来
         *      Object get(int index):获取指定index位置的元素
         *      int indexOf(Object obj):返回obj在集合中首次出现的位置
         *      int lastIndexOf(Object obj):返回obj在当前集合中末次出现的位置
         *      Object remove(int index):移除指定index位置的元素，并返回此元素
         *      Object set(int index, Object ele):设置指定index位置的元素为ele
         *      List subList(int fromIndex, int toIndex):返回从fromIndex到toIndex
         *      位置的子集合
         */

        /**List实现类之一：ArrayList
         * ArrayList 是 List 接口的典型实现类、主要实现类
         * 本质上，ArrayList是对象引用的一个”变长”数组
         * ArrayList的JDK1.8之前与之后的实现区别？
         *      //饿汉式 ，，， 能先造就先造
         *       JDK1.7：ArrayList像【饿汉式】，直接创建一个【初始容量为10的数组】
         *      //拖延症，，，可以多迟造就多迟造
         *       JDK1.8：ArrayList像【懒汉式】，一开始【创建一个长度为0的数组，当添加第一个元
         *      素时再创建一个始容量为10的数组】
         *
         *      // ---->转换成List集合
         * Arrays.asList(…) 方法返回的 List 集合，【既不是 ArrayList 实例】，【也不是Vector 实例】。
         *  Arrays.asList(…) 返回值是一个【固定长度的 List 集合】
         */
        ArrayList<Object> list = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();

        /**父接口List也有的方法，包括List接口实现的Collection接口的所有方法演示*/
        //从Collection【父父接口】中继承来的方法

        //添加
        list.add("111");
        list.add("11");

        list2.add("22");
        list2.add("222");

        list.addAll(list2);

        System.out.println(list.size());//4

        //迭代
        /** 【Iterable迭代接口被Collection继承】    */
        //iterator
        Iterator<Object> iterator = list.iterator();
        //一开始指针在-1处,每一次next()都会自动把指针下移一位
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //清空集合
        list.clear();
        System.out.println(list.size()); // 0
        System.out.println(list.isEmpty());//判读是否为空  //true

        list.add(list2);
        list.add("111");
        list.add("11");


        //contains 看集合中是否包含此元素
        System.out.println(list.contains("111"));//true，包含

        //containsAll用来比较[两个集合] [是否一样]  //不是看包含，是比较两个集合 //底层一个一个元素比较
        //List中是按元素的equals来比较每一个元素的
        ArrayList<Object> list3 = new ArrayList<>();
        list3.addAll(list);
        System.out.println(list.containsAll(list3));//true,, containsAll就不是看包含了，而是看两集合是否相等


        Iterator<Object> iterator1 = list2.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next());
            //22 222
        }


        //删除
//        删除对应的元素
        list3.remove("111");

        //删除两集合的差集部分
        // 即 【B [交集] A】 ----> 【B 交集】  ,以左为主，保主和交集部分，右边从集合的没包含进主集合的被删除(即右差集删除)
        list3.removeAll(list2);

        System.out.println(list3);//[[22, 222], 11]

        System.out.println(list2);//[22, 222]

        ArrayList<Object> list4 = new ArrayList<>();
        list4.add("11");



//        取两个集合的交集
// [[22, 222], 11] ----- > A[[22, 222], 11] 交 B[11]--->取出交集部分[11]--->将交集集合覆盖原来的主集合
        list3.retainAll(list4);//将交集的结果存进当前从集合，就是覆盖list3

        System.out.println(list3);//11

        /**注意:
         * list1.addAll(list2);中list1中加入list2 ---->  list1[list2(对象元素),。。。]
         *
         *
         * 一种情况  list1[list3,"11"] 交 list3["22","222"]
         *    list1.retainAll(list3); //此处两个主要的集合就是list1和list3,主要比较里面元素
         *    list1的第一个元素 list3 比较的不是 list3，而是list3中的第一个元素“22”，那肯定不一样
         *
         *
         *
         * */


        //equals && containsAll
        //equals 是根据两对象的equals方法来比较，，，containsAll是比较两个集合中的元素是否一一相等
        //可以发现
        /**equals和 containsAll方法都是比较两个集合之间的一一元素是否相等*/
        list4.clear();
        list4.add("11");

        System.out.println(list3.containsAll(list4));//true
        System.out.println(list3.equals(list4));//true

        //Object[] toArray()  将集合转化成数组返回
        System.out.println(Arrays.toString(list4.toArray()));//11

        //hashCode() 方法返回集合的hash值
        System.out.println(list4.hashCode());//1599

        //将对应的index索引上的值插入对应的值 //注意:是插入，不是替换，把原先位置上的元素往后移
        list4.add(0,"4");
        System.out.println(list4);//[4,11] //0索引上的11移动到后一位,让开位置让新元素加入


        Iterator<Object> iterator2 = list4.iterator();
        while (iterator2.hasNext()) {//指针从-1开始下移

            System.out.println(iterator2.next());

            //删除当前指针指向的元素
            iterator2.remove();

            //因为删除过了当前指针的元素，而且没有将指针下移，所以再删除是出错的java.lang.IllegalStateException异常
//            iterator2.remove();
        }

        System.out.println(list4);//[]
        list4.add("4");
        list4.add("44");
        list4.add("444");


        //增强for循环底层还是使用的 Iterable 迭代器来迭代的
        for (Object o : list4) {
            System.out.println(o);
        }

//        也是增强for循环，底层还是使用迭代器Iterable来迭代
        list4.forEach(str -> System.out.println(str));

        //带index索引的addAll不会将list作为对象存去，而是把list3里面的元素拿出来拼接
        list4.addAll(3,list3);//index==3 从0 1 2 3 即第4个元素覆盖第4个元素开始后面拼接，左闭右闭，[]
        System.out.println(list4);

        System.out.println(list4.get(3));
        list4.add("11");

        //indexOf ---> 11  获取在集合中第一次出现的索引
        System.out.println(list4.indexOf("11"));//3

        // lastIndexOf
        System.out.println(list4.lastIndexOf("11"));//最后一次出现的位置

        System.out.println(list4);

        //移除并返回对应索引上的元素
        Object remove = list4.remove(3);
        System.out.println(remove);//移除

        System.out.println(list4);


        list4.set(1,"41");//将1索引上的 44 ---改成--- 41
        System.out.println(list4);


        //subList   按 索引 取子集合，左闭右开 [,)
        //将 索引范围内的子集合拿出来,,左闭右开,,, [1索引上,到2索引前一位),,不移除,只取出
        System.out.println(list4.subList(1, 2));


        //Arrays.asList(集合)  -----> 转换成list集合
        int[] in1 = new int[]{1,2,3,4};
        List<int[]> ints = Arrays.asList(in1);

        /**因为集合内是 不 存储基本数据类型的，，一般是存储引用数据类型的*/

        //迭代
        Iterator<int[]> iterator3 = ints.iterator();
        while (iterator3.hasNext()) {
            int[] next = iterator3.next();
            System.out.println(Arrays.toString(next));//[1, 2, 3, 4]
        }

        List<String> strings = Arrays.asList("123", "234", "456");
        System.out.println(strings);//[123, 234, 456]

        List<? extends Serializable> serializables = Arrays.asList("123", 78, 453, true);
        System.out.println(serializables);//[123, 234, 456]


        /**LinkedList  :  底层使用的是链表，很方便频繁的删除插入，不太适合查看
         * ArrayList   :  底层使用的是数组，可以直接查看，但对于频繁更新不适合，因为插入和删除等都需要其他位置的移动来适配
         *
         * */
        LinkedList<Object> linkedList = new LinkedList<>();


        /**双向链表*/
        // First ... ... ... ... Last   左First右Last

//        队列  【队头出,队尾加】
        //尾添加
        linkedList.addLast("1");
        linkedList.addLast("2");
        linkedList.addLast("3");
        linkedList.addLast("4");

        //头取出,不删除，只是复制拿出
        System.out.println(linkedList.getFirst()); // 1
        System.out.println(linkedList);

        //removeFirst //头删除取出
        System.out.println(linkedList.removeFirst());//1 删除取出
        System.out.println(linkedList);//1 2 3

        //清空
        linkedList.clear();


//     First ... ... ... ... Last   左First右Last
//        头添加
        linkedList.addFirst("1");
        linkedList.addFirst("2");
        linkedList.addFirst("3");
        linkedList.addFirst("4");

        System.out.println(linkedList);// 4,3,2,1

//        尾取出
        System.out.println(linkedList.getLast());//1

//        尾删除取出
        Object o = linkedList.removeLast();
        System.out.println(o);//1


        /**List 实现类之三：Vector
         *
         * Vector 是一个古老的集合，JDK1.0就有了。大多数操作与ArrayList
         * 相同，区别之处在于Vector是线程安全的。
         *
         * 在各种list中，最好把ArrayList作为缺省选择。当插入、删除频繁时，
         * 使用LinkedList；Vector总是比ArrayList慢，所以尽量避免使用。
         *
         * 新增方法：
         *       void addElement(Object obj)
         *       void insertElementAt(Object obj,int index)
         *       void setElementAt(Object obj,int index)
         *       void removeElement(Object obj)
         *       void removeAllElements()
         * */

        //Vector 和 ArrayList集合最大的区别就是
        // Vector线程安全的，ArrayList线程不安全  Vector 比 ArrayList慢
        Vector<Object> objects = new Vector<>();
        objects.addElement("1");
        objects.addElement("3");

        objects.insertElementAt("2",1);//插入


        objects.setElementAt("2.5",1);//修改指定索引上的元素
        objects.remove("2.5");
        objects.remove("3");
        objects.add("2");
        objects.add("3");
        Vector<Object> objects1 = new Vector<>();
        objects1.addElement("2");
        objects1.addElement("3");
        objects1.addElement("4");


        objects.removeAll(objects1);//将和object1的交集删除

        System.out.println(objects1);//不碰第二个从集合
        System.out.println(objects);//将对应的集合对应自己的子集来删除

        /**请问ArrayList/LinkedList/Vector的异同？谈谈你的理解？ArrayList底层
         * 是什么？扩容机制？Vector和ArrayList的最大区别?
         *
         *
         *  ArrayList和LinkedList的异同
         * 二者都线程不安全，相对线程安全的Vector，执行效率高。
         * 此外，ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。对于
         * 随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。对于新增
         * 和删除操作add(特指插入)和remove，LinkedList比较占优势，因为ArrayList要移动数据。
         *
         *  ArrayList和Vector的区别
         * Vector和ArrayList几乎是完全相同的,唯一的区别在于Vector是【同步类(synchronized)】，属于
         * 强同步类。因此开销就比ArrayList要大，访问要慢。
         *
         * 正常情况下,大多数的Java程序员使用ArrayList而不是Vector,因为同步完全可以由程序员自己来控制。
         * Vector 【每次扩容请求其大小的2倍空间，而ArrayList是1.5倍】。Vector还有一个子类Stack。
         *
         *
         * */


    }




    /**Collection接口
     *          |---Set接口
     *
     *  Set接口是Collection的子接口，set接口没有提供额外的方法
     *  Set 集合【不允许包含相同的元素】，如果试把两个相同的元素加入同一个
     *
     *
     * Set 集合中，则添加操作失败。
     *  Set 【判断两个对象是否相同不是使用 == 运算符，而是根据 equals() 方法】
     *
     * 向HashSet中添加元素的过程：
     *       当向 HashSet 集合中存入一个元素时，
     *      1: HashSet 会调用该对象的 hashCode() 方法来得到该对象的 hashCode 值
     *      ，然后根据 hashCode 值，通过某种散列函数决定该对象
     *      在 HashSet 底层数组中的存储位置。
     *      （这个散列函数会与底层数组的长度相计算得到在
     *      数组中的下标，并且这种散列函数计算还尽可能保证能均匀存储元素，越是散列分布，
     *      该散列函数设计的越好）//【根据当前哈希数组长度来计算当前元素的位置，
     *      比如: [元素哈希值 / 数组长度等算法 ----->计算出要放置的数组坐标位置]  】
     *
     *       如果两个元素的hashCode()值相等，会再继续调用equals方法，如果equals方法结果
     *      为true，添加失败；如果为false，那么会保存该元素，但是该数组的位置已经有元素了，
     *      那么会通过链表的方式继续链接。
     *
     * 如果两个元素的 equals() 方法返回 true，但它们的 hashCode() 返回值不相
     * 等，hashSet 将会把它们存储在不同的位置，但依然可以添加成功。
     *
     *
     *
     * Set实现类之一：HashSet
     *      底层也是数组，初始容量为16，当如果使用率超过0.75，（16*0.75=12）
     *      就会扩大容量为原来的2倍。（16扩容为32，依次为64,128....等）
     *
     *
     * 重写 hashCode() 方法的基本原则
     *       在程序运行时，同一个对象多次调用 hashCode() 方法应该返回相同的值。
     *       当两个对象的 equals() 方法比较返回 true 时，这两个对象的 hashCode()
     *      方法的返回值也应相等。
     *       对象中用作 equals() 方法比较的 Field，都应该用来计算 hashCode 值。
     *
     *
     * 以自定义的Customer类为例，何时需要重写equals()？
     *       当一个类有自己特有的“逻辑相等”概念,当改写equals()的时候，总是
     *      要改写hashCode()，根据一个类的equals方法（改写后），两个截然不
     *      同的实例有可能在逻辑上是相等的，但是，根据Object.hashCode()方法，
     *      它们仅仅是两个对象。
     *       因此，违反了“相等的对象必须具有相等的散列码”。
     *       结论：复写equals方法的时候一般都需要同时复写hashCode方法。通
     *      常参与计算hashCode的对象的属性也应该参与到equals()中进行计算。
     *
     *
     *
     * Eclipse/IDEA工具里hashCode()的重写
     * 以Eclipse/IDEA为例，在自定义类中可以调用工具自动重写equals和hashCode。
     * 问题：为什么用Eclipse/IDEA复写hashCode方法，有31这个数字？
     *       选择系数的时候要选择尽量大的系数。因为如果计算出来的hash地址越大，所谓的
     *      “冲突”就越少，查找起来效率也会提高。（减少冲突）
     *       并且31只占用5bits,相乘造成数据溢出的概率较小。
     *       31可以 由i*31== (i<<5)-1来表示,现在很多虚拟机里面都有做相关优化。（提高算法效
     *      率）
     *       31是一个素数，素数作用就是如果我用一个数字来乘以这个素数，那么最终出来的结
     *      果只能被素数本身和被乘数还有1来整除！(减少冲突)
     *
     *
     *
     * Set实现类之二：LinkedHashSet
     *      LinkedHashSet 是 HashSet 的子类
     *      LinkedHashSet 根据元素的 hashCode 值来决定元素的存储位置，
     *      但它同时使用双向链表维护元素的次序，这使得元素看起来是以插入
     *      顺序保存的。
     *      LinkedHashSet插入性能略低于 HashSet，但在迭代访问 Set 里的全
     *      部元素时有很好的性能。
     *      LinkedHashSet 不允许集合元素重复。
     *
     *
     * Set实现类之三：TreeSet
     * TreeSet 是 SortedSet 接口的实现类，TreeSet 可以确保集合元素处于排序状态。
     * TreeSet底层使用红黑树结构存储数据
     *  新增的方法如下： (了解) Comparator comparator()
     * Object first()
     * Object last()
     * Object lower(Object e)
     * Object higher(Object e)
     * SortedSet subSet(fromElement, toElement) SortedSet headSet(toElement) SortedSet tailSet(fromElement)
     *
     *
     * TreeSet 两种排序方法：自然排序和定制排序。默认情况下，TreeSet 采用自然排序。
     *
     *
     *
     * TreeSet和后面要讲的TreeMap
     * 采用红黑树的存储结构
     *  特点：有序，查询速度比List快
     * */

    @Test
    public void test02(){




    }









}
