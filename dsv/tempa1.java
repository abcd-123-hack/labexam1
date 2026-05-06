package tempa;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class tempa1 {
	
	public static class MapperClass
    extends Mapper<LongWritable, Text, Text, FloatWritable> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] fields = value.toString().split("\\s+");
        float temp = Float.parseFloat(fields[1]);
        context.write(new Text("temp"), new FloatWritable(temp));
    }
}

public static class ReducerClass
    extends Reducer<Text, FloatWritable, Text, Text> {

    public void reduce(Text key, Iterable<FloatWritable> values,
                       Context context)
            throws IOException, InterruptedException {

        float max = Float.MIN_VALUE;
        float min = Float.MAX_VALUE;

        for (FloatWritable v : values) {
            max = Math.max(max, v.get());
            min = Math.min(min, v.get());
        }

        context.write(new Text("Max"), new Text(String.valueOf(max)));
        context.write(new Text("Min"), new Text(String.valueOf(min)));
    }
}

public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "global max min");

    job.setJarByClass(tempa1.class);
    job.setMapperClass(MapperClass.class);
    job.setReducerClass(ReducerClass.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FloatWritable.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
}
}
