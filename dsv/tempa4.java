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

public class tempa4 {

	public static class MapperClass
    extends Mapper<LongWritable, Text, Text, FloatWritable> {

    private String targetCity;

    protected void setup(Context context) {
        targetCity = context.getConfiguration().get("city");
    }

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        String[] fields = value.toString().split("\\s+");
        String city = fields[2];
        float temp = Float.parseFloat(fields[1]);

        if (city.equals(targetCity)) {
            context.write(new Text(city), new FloatWritable(temp));
        }
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
    conf.set("city", args[2]);   // third argument

    Job job = Job.getInstance(conf, "any city max min");

    job.setJarByClass(tempa4.class);
    job.setMapperClass(MapperClass.class);
    job.setReducerClass(ReducerClass.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(FloatWritable.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
}

}
