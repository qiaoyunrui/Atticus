package guava;

import com.google.common.base.Charsets;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GuavaDemo {

    public void function() {
        Optional<Integer> optional = Optional.of(32);
//        boolean isPresent = optional.isPresent();
//        int value = optional.get();
        optional.ifPresent(System.out::println);    // 不为空就打印

        String output = MoreObjects.toStringHelper(optional).add("author", "Juhezi").addValue("weibo")
                .addValue("kuaishou").toString();
        System.out.println(output);
        ImmutableSet<String> set = ImmutableSet.of("A", "B", "C", "D", "E", "F");
        System.out.println(set);
        set.stream().map(input -> Strings.repeat(input, 2)).forEach(System.out::println);
        // 一套缓存框架，害挺好用的
        LoadingCache<String, Integer> scores = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .removalListener((RemovalListener<String, Integer>) notification -> {
                    // TODO: 2020/8/26 被移除的回调
                })
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(@NotNull String key) {
                        return Optional.of(key).map(String::length).orElse(-1);
                    }
                });
        System.out.println(MoreObjects
                .toStringHelper("Length")
                .add("Juhezi", scores.getUnchecked("Juhezi"))
                .add("NiceToMeetYou", scores.getUnchecked("NiceToMeetYou"))
                .toString());
        HashFunction hashFunction = Hashing.sha256();
        HashCode hashCode = hashFunction.newHasher()
                .putLong(12128L)
                .putString("Juhezi", Charsets.UTF_8)
                .hash();
    }

    public static class DemoList<E> extends ForwardingCollection<E> {

        final List<E> delegate = Lists.newArrayList();

        @Override
        protected Collection<E> delegate() {
            return delegate;
        }

        @Override
        public boolean add(@NotNull E element) {
            // TODO: 2020/8/26 do something
            return super.add(element);
        }
    }

}
